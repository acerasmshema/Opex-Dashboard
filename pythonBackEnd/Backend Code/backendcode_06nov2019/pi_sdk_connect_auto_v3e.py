# =============================================================================
# Installations:
# python 3.6
# pip install pythonnet
# pip install dateparser
# ============================================================================

import datetime
# =============================================================================
# Import libraries
# =============================================================================
import sys
from datetime import timedelta
from timeit import default_timer as timer

import clr
import numpy as np
import pandas as pd
from forward_predict_3e import forward_predict
from sqlalchemy import create_engine
from zf_organize_data_v3d import get_fr, get_imp, get_hbl, get_hwl, get_ddf, get_dpf, allign_cycles, get_mltable, \
    rename_dict, get_outpredictors

engine = create_engine('postgresql://postgres:admin123@localhost:5432/digester')
#engine2 = create_engine('postgresql://postgres:postgresACERASadmin@172.16.232:5432/digester')
#mltable_2.to_sql('crossservertrial',engine2, if_exists="replace")
from apscheduler.schedulers.blocking import BlockingScheduler
import functools
from sklearn.preprocessing import StandardScaler
import h2o
try:
    h2o.init()
except:
    h2o.connect()


sys.path.append(r'C:\Program Files (x86)\PIPC\AF\PublicAssemblies\4.0')    
clr.AddReference('OSIsoft.AFSDK') 

from OSIsoft.AF import *  
from OSIsoft.AF.PI import *  
from OSIsoft.AF.Asset import *  
from OSIsoft.AF.Data import *  
from OSIsoft.AF.Time import *  
from OSIsoft.AF.UnitsOfMeasure import *  


# =============================================================================
# Setup Server
# =============================================================================
piServers = PIServers()    
piServer = piServers.DefaultPIServer
print("Server: {}".format(piServer))
        

# =============================================================================
# Extraction functions
# =============================================================================
def pi_interpolated_value(piServer='RAPP1',
                          pi_tag_list=[],
                          start_time='2019-01-01 00:00:00',
                          end_time='2019-01-02 00:00:00',
                          freq="1h",
                          server_timebased = True,
                          convert_invalid_to_nan = False,
                          datetime_index = False,
                          convert_col_to_lower = True):
    '''
    This function extracts interpolated data (Sampled)
    '''
    
    global error_detector
    
    download_process_start_time = timer()
    df_combined = pd.DataFrame()
    
    #if len(pi_tag_list)>10:
    #    print("For load control purposes, only the first 10 tags will be extracted")
        
    if type(pi_tag_list) not in [list,tuple]:
        pi_tag_list = [pi_tag_list]
        
    
    if server_timebased == True:
        #Adjust timezone:
        if str(piServer) == 'RAPP1': #Add one hour, cause SDK will bring it back 1 hour
            start_time = (pd.to_datetime(start_time) + timedelta(hours=1)).strftime("%Y-%m-%d %H:%M:%S")
            if pd.to_datetime(end_time) > (datetime.datetime.now()-timedelta(hours=1)):
                end_time = (datetime.datetime.now()).strftime("%Y-%m-%d %H:%M:%S")
            else:
                end_time = (pd.to_datetime(end_time) + timedelta(hours=1)).strftime("%Y-%m-%d %H:%M:%S")
        
        
    for pi_tag in pi_tag_list:
        span = freq
        try:
            pt = PIPoint.FindPIPoint(piServer, pi_tag) 
            timerange = AFTimeRange(start_time, end_time)  
            span = AFTimeSpan.Parse(span)  
            interpolated = pt.InterpolatedValues(timerange, span, "", False)
            data = [(str(event.Timestamp.LocalTime),event.Value) for event in interpolated]
            df = pd.DataFrame(data,columns=['datetime',str(pi_tag)])
            df['datetime'] = pd.to_datetime(df['datetime'])
            if len(df_combined) == 0:
                df_combined = df.copy()
            else:
                df_combined = pd.merge(left=df_combined, right=df, how='left', on='datetime')
                    
        except:
            print("Extraction error: {}".format(pi_tag))
            error_detector = 1
            break
    download_process_end_time = timer()
    process_time = download_process_end_time - download_process_start_time
    print("Process finished in {:.1f} seconds".format(process_time))
    
    if server_timebased == True:
        #Adjust back timezone:
        if len(df_combined)>0:
            if str(piServer) == 'RAPP1': #Change timezone from MY to IND
                df_combined['datetime'] = df_combined['datetime'] - timedelta(hours=1)
            
            
    #covert from AF object to string for non-valid data:
    for col in df_combined.columns:
        if col != 'datetime':
            df_combined[col] = df_combined[col].apply(lambda x:str(x) if not isinstance(x,float) else x)
            
            
    #Convert str to np.nan for invalid data:
    if convert_invalid_to_nan == True:
        for col in df_combined.columns:
            if col != 'datetime':
                df_combined[col] = df_combined[col].apply(lambda x:np.nan if isinstance(x,str) else x)
                df_combined[col].replace([np.inf, -np.inf], np.nan, inplace=True)
    
    #Convert col to lowercase:
    if convert_col_to_lower == True:
        colnames = [col.lower() for col in df_combined.columns]
        df_combined.columns = colnames
            
    
    if datetime_index == True:
        df_combined.set_index(['datetime'],inplace=True)
        
        
    return df_combined.copy()
                
                

##########################################################################
def pi_as_recorded_value(piServer='RAPP1',pi_tag='',
                         start_time='2019-01-01 00:00:00',
                         end_time='2019-01-02 00:00:00',
                         server_timebased = True,
                         convert_invalid_to_nan = False,
                         datetime_index = False,
                         convert_col_to_lower = True):
    '''
    This function extracts as_recorded data (Compressed)
    '''
    download_process_start_time = timer()
    
    if server_timebased == True:
        #Adjust timezone:
        if str(piServer) == 'RAPP1': #Add one hour, cause SDK will bring it back 1 hour
            start_time = (pd.to_datetime(start_time) + timedelta(hours=1)).strftime("%Y-%m-%d %H:%M:%S")
            end_time = (pd.to_datetime(end_time) + timedelta(hours=1)).strftime("%Y-%m-%d %H:%M:%S")
        
    df = pd.DataFrame()
    try:
        if type(pi_tag) in [list,tuple]:
            pi_tag = pi_tag[0]
            
        timerange = AFTimeRange(start_time, end_time)
        pt = PIPoint.FindPIPoint(piServer, pi_tag) 
        recorded = pt.RecordedValues(timerange, AFBoundaryType.Inside, "", False)  
        data = [(str(event.Timestamp.LocalTime),event.Value) for event in recorded]
        df = pd.DataFrame(data,columns=['datetime',str(pi_tag)])
        df['datetime'] = pd.to_datetime(df['datetime'])
    except:
        print("Tag extraction error: {}".format(pi_tag))
        
    download_process_end_time = timer()
    process_time = download_process_end_time - download_process_start_time
    print("Process finished in {:.1f} seconds".format(process_time))
    
    
    if server_timebased == True:
        #Adjust back timezone:
        if len(df)>0:
            if str(piServer) == 'RAPP1': #Change timezone from MY to IND
                df['datetime'] = df['datetime'] - timedelta(hours=1)
            
            
    #covert from AF object to string for non-valid data:
    for col in df.columns:
        if col != 'datetime':
            df[col] = df[col].apply(lambda x:str(x) if not isinstance(x,float) else x)
            
    #Convert str to np.nan for invalid data:
    if convert_invalid_to_nan == True:
        for col in df.columns:
            if col != 'datetime':
                df[col] = df[col].apply(lambda x:np.nan if isinstance(x,str) else x)
                df[col].replace([np.inf, -np.inf], np.nan, inplace=True)
                
    #Convert col to lowercase:
    if convert_col_to_lower == True:
        colnames = [col.lower() for col in df.columns]
        df.columns = colnames
        
    
    if datetime_index == True:
        df.set_index(['datetime'],inplace=True)
        
    return df.copy()





# =============================================================================
# Extraction
# =============================================================================
    

imp_tag_list = ["RPL.412D01FIL:value","RPL.412D03FIL:value","RPL.412D05FIL:value","RPL.412D07FIL:value","RPL.412D09FIL:value","RPL.412D11FIL:value","RPL.412D13FIL:value",
"RPL.412D02FIL:value","RPL.412D04FIL:value","RPL.412D06FIL:value","RPL.412D08FIL:value","RPL.412D10FIL:value","RPL.412D12FIL:value","RPL.412D14FIL:value"]

hbl_tag_list = ["RPL.412D01FHBL:value","RPL.412D03FHBL:value","RPL.412D05FHBL:value","RPL.412D07FHBL:value","RPL.412D09FHBL:value","RPL.412D11FHBL:value","RPL.412D13FHBL:value",
"RPL.412D02FHBL:value","RPL.412D04FHBL:value","RPL.412D06FHBL:value","RPL.412D08FHBL:value","RPL.412D10FHBL:value","RPL.412D12FHBL:value","RPL.412D14FHBL:value"]

hwl_tag_list = ["RPL.412D01FHWL:value","RPL.412D03FHWL:value","RPL.412D05FHWL:value","RPL.412D07FHWL:value","RPL.412D09FHWL:value","RPL.412D11FHWL:value","RPL.412D13FHWL:value",
"RPL.412D02FHWL:value","RPL.412D04FHWL:value","RPL.412D06FHWL:value","RPL.412D08FHWL:value","RPL.412D10FHWL:value","RPL.412D12FHWL:value","RPL.412D14FHWL:value",]

dpf_tag_list = ["RPL.412D01FDPL:value","RPL.412D03FDPL:value","RPL.412D05FDPL:value","RPL.412D07FDPL:value","RPL.412D09FDPL:value","RPL.412D11FDPL:value","RPL.412D13FDPL:value",
"RPL.412D02FDPL:value","RPL.412D04FDPL:value","RPL.412D06FDPL:value","RPL.412D08FDPL:value","RPL.412D10FDPL:value","RPL.412D12FDPL:value","RPL.412D14FDPL:value",]

ddf_tag_list = ["RPL.412D01FDIL:value","RPL.412D03FDIL:value","RPL.412D05FDIL:value","RPL.412D07FDIL:value","RPL.412D09FDIL:value","RPL.412D11FDIL:value","RPL.412D13FDIL:value",
"RPL.412D02FDIL:value","RPL.412D04FDIL:value","RPL.412D06FDIL:value","RPL.412D08FDIL:value","RPL.412D10FDIL:value","RPL.412D12FDIL:value","RPL.412D14FDIL:value"]

hbl_eff_list = ["RPL.412D01_EFF:value", "RPL.412D03_EFF:value", "RPL.412D05_EFF:value", "RPL.412D07_EFF:value", "RPL.412D09_EFF:value", "RPL.412D11_EFF:value", "RPL.412D13_EFF:value",
"RPL.412D02_EFF:value", "RPL.412D04_EFF:value", "RPL.412D06_EFF:value", "RPL.412D08_EFF:value", "RPL.412D10_EFF:value", "RPL.412D12_EFF:value", "RPL.412D14_EFF:value",]


#### Kappa number and blow tank value
kappa_blow = ["RPL.422AT2013B:value","RPL.422LT1016:value"]

df_flkp_tag_list = imp_tag_list + hbl_tag_list + hwl_tag_list + dpf_tag_list + ddf_tag_list + kappa_blow + hbl_eff_list

del(imp_tag_list, hbl_tag_list, hwl_tag_list, dpf_tag_list, ddf_tag_list, kappa_blow, hbl_eff_list)

temp_tag_list = ["RPL.412TT0115:value","RPL.412TT0315:value","RPL.412TT0515:value","RPL.412TT0715:value","RPL.412TT0915:value","RPL.412TT1115:value","RPL.412TT1315:value",
"RPL.412TT0215:value","RPL.412TT0415:value","RPL.412TT0615:value","RPL.412TT0815:value","RPL.412TT1015:value","RPL.412TT1215:value","RPL.412TT1415:value",
"RPL.412TT0123:value","RPL.412TT0323:value","RPL.412TT0523:value","RPL.412TT0723:value","RPL.412TT0923:value","RPL.412TT1123:value","RPL.412TT1323:value",
"RPL.412TT0223:value","RPL.412TT0423:value","RPL.412TT0623:value","RPL.412TT0823:value","RPL.412TT1023:value","RPL.412TT1223:value","RPL.412TT1423:value",
"RPL.412TT0118:value","RPL.412TT0318:value","RPL.412TT0518:value","RPL.412TT0718:value","RPL.412TT0918:value","RPL.412TT1118:value","RPL.412TT1318:value",
"RPL.412TT0218:value","RPL.412TT0418:value","RPL.412TT0618:value","RPL.412TT0818:value","RPL.412TT1018:value","RPL.412TT1218:value","RPL.412TT1418:value"
]

pres_tag_list = ["RPL.412PT0106:value","RPL.412PT0306:value","RPL.412PT0506:value","RPL.412PT0706:value","RPL.412PT0906:value","RPL.412PT1106:value","RPL.412PT1306:value",
"RPL.412PT0206:value","RPL.412PT0406:value","RPL.412PT0606:value","RPL.412PT0806:value","RPL.412PT1006:value","RPL.412PT1206:value","RPL.412PT1406:value",
]

washer_flow_rate_tag_list = ["RPL.422FC1201:mv","RPL.422FC1202:mv","RPL.422FC1228:mv","RPL.422FC1229:mv","RPL.422FC1301:mv","RPL.422FC1302:mv",
                           "RPL.422FC1328:mv","RPL.422FC1329:mv","RPL.422FC1801:mv","RPL.422FC1802:mv","RPL.422FC1803:mv","RPL.422FC1811:mv",
                           "RPL.422LC1258:mv","RPL.422LC1403:mv",
                           "RPL.422FC1257:mv","RPL.422FC1263:mv",
                           "RPL.422FC1402:mv",
                           "RPL.422FC4057:mv",      ##flow from SP4 to SP6
                           "RPL.422LT4050:value",   ##SP4 volume in %
]

## H-factor
h_factor_tag_list = ["RPL.412D01H:value","RPL.412D03H:value",
                     "RPL.412D05H:value","RPL.412D07H:value",
                     "RPL.412D09H:value","RPL.412D11H:value",
                     "RPL.412D13H:value","RPL.412D02_H:value",
                     "RPL.412D04_H:value","RPL.412D06_H:value",
                     "RPL.412D08_H:value","RPL.412D10_H:value",
                     "RPL.412D12_H:value","RPL.412D14_H:value",
        ]
### H-factor Temp
h_factor_temp_tag_list = ["RPL.412D01HTMP:value","RPL.412D03HTMP:value",
                          "RPL.412D05HTMP:value","RPL.412D07HTMP:value",
                          "RPL.412D09HTMP:value","RPL.412D11HTMP:value",
                          "RPL.412D13HTMP:value","RPL.412D02HTMP:value",
                          "RPL.412D04HTMP:value","RPL.412D06HTMP:value",
                          "RPL.412D08HTMP:value","RPL.412D10HTMP:value",
                          "RPL.412D12HTMP:value","RPL.412D14HTMP:value",
                          ]

## Tags for QA lab data. In sequence, TAA, TTA, Sulphidity,Caustisizer Eff., TSS, SO4, AA Charge, HWL vol set point
white_liquor_qa_tag_list = ["472XI009LP", "472XI010LP",
                            "472XI011LP", "472XI013LP",
                            "472XI014LP", "472XI042LP",
                            "RPL.412ALKWR:value","RPL.412ALKCONC:value",
                            ]
#### Dig weight after chip filling in ton
dig_weight_tag_list = ['RPL.412D01WACHF:value', 'RPL.412D03WACHF:value',
                       'RPL.412D05WACHF:value', 'RPL.412D07WACHF:value',
                       'RPL.412D09WACHF:value', 'RPL.412D11WACHF:value',
                       'RPL.412D13WACHF:value', 'RPL.412D02WACHF:value',
                       'RPL.412D04WACHF:value', 'RPL.412D06WACHF:value',
                       'RPL.412D08WACHF:value', 'RPL.412D10WACHF:value',
                       'RPL.412D12WACHF:value', 'RPL.412D14WACHF:value',
                       ]

df_flkp = pd.DataFrame()
df_temp = pd.DataFrame()
df_pres = pd.DataFrame()
df_wash = pd.DataFrame()
df_qa = pd.DataFrame()
df_hf = pd.DataFrame()
df_weight = pd.DataFrame()


##### Selecting variable, getting model predictors and renaming columns to be user friendly
#predictors = get_predictors()
var_cols = rename_dict()
out_predictors = get_outpredictors()

y          = 'pre o2 kappa'

def refresh_data():
    
    print("Executing refresh_data now:" + datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    
    error_detector = 0
    
    live = datetime.datetime.now()
    live = live - datetime.timedelta(seconds=live.second %20)
    
    global df_flkp
    global df_temp
    global df_pres
    global df_wash
    global df_qa
    global mltable
    global reportÔ
    global df_hf
    global df_weight
#    global dgtr_no
    global mltable_2
#    global error_detector
    
    if df_flkp.empty:
        print("DataFrame is empty. Extracting 2 days of data now.")
        start_time = (live - timedelta(days=2)).strftime("%Y-%m-%d %H:%M:%S")
        end_time = (live - timedelta(days=0)).strftime("%Y-%m-%d %H:%M:%S")
        
    else:
        print("Pulling latest data")
        start_time = (df_flkp.iloc[-1]['datetime'] +timedelta(seconds=20)).strftime("%Y-%m-%d %H:%M:%S")
        end_time = (live - timedelta(days=0)).strftime("%Y-%m-%d %H:%M:%S")
                
    current_flkp = pi_interpolated_value(piServer,
                                   pi_tag_list = df_flkp_tag_list,
                                   start_time = start_time,
                                   end_time = end_time,
                                   freq = "20s",
                                   server_timebased = False,
                                   convert_invalid_to_nan = True,
                                   datetime_index = False,
                                   convert_col_to_lower = False)
    if error_detector == 1:
        print('error encocuntered')
    
    else: 
        df_flkp = pd.concat([df_flkp,current_flkp]).reset_index(drop=True)
        
        ####Blowtank lvl NaN causes an error when calculating liquid flow
        df_flkp['RPL.422LT1016:value'] = df_flkp['RPL.422LT1016:value'].fillna(method='ffill')
    
        
        current_temp = pi_interpolated_value(piServer,
                                       pi_tag_list = temp_tag_list,
                                       start_time = start_time,
                                       end_time = end_time,
                                       freq = "20s",
                                       server_timebased = False,
                                       convert_invalid_to_nan = True,
                                       datetime_index = False,
                                       convert_col_to_lower = False)
        
        df_temp = pd.concat([df_temp,current_temp]).reset_index(drop=True)
        
        current_pres = pi_interpolated_value(piServer,
                                       pi_tag_list = pres_tag_list,
                                       start_time = start_time,
                                       end_time = end_time,
                                       freq = "20s",
                                       server_timebased = False,
                                       convert_invalid_to_nan = True,
                                       datetime_index = False,
                                       convert_col_to_lower = False)
        
        df_pres= pd.concat([df_pres,current_pres]).reset_index(drop=True)
        
        current_wash_fr = pi_interpolated_value(piServer,
                                       pi_tag_list = washer_flow_rate_tag_list,
                                       start_time = start_time,
                                       end_time = end_time,
                                       freq = "20s",
                                       server_timebased = False,
                                       convert_invalid_to_nan = True,
                                       datetime_index = False,
                                       convert_col_to_lower = False)
        
        df_wash = pd.concat([df_wash,current_wash_fr]).reset_index(drop=True)
        
        current_qa = pi_interpolated_value(piServer,
                                       pi_tag_list = white_liquor_qa_tag_list,
                                       start_time = start_time,
                                       end_time = end_time,
                                       freq = "20s",
                                       server_timebased = False,
                                       convert_invalid_to_nan = True,
                                       datetime_index = False,
                                       convert_col_to_lower = False)
        
        df_qa = pd.concat([df_qa,current_qa]).reset_index(drop=True)
    
        current_hf = pi_interpolated_value(piServer,
                                       pi_tag_list = h_factor_tag_list,
                                       start_time = start_time,
                                       end_time = end_time,
                                       freq = "20s",
                                       server_timebased = False,
                                       convert_invalid_to_nan = True,
                                       datetime_index = False,
                                       convert_col_to_lower = False)
        
        df_hf = pd.concat([df_hf, current_hf]).reset_index(drop=True)
        
        
        current_weight = pi_interpolated_value(piServer,
                                       pi_tag_list = dig_weight_tag_list,
                                       start_time = start_time,
                                       end_time = end_time,
                                       freq = "20s",
                                       server_timebased = False,
                                       convert_invalid_to_nan = True,
                                       datetime_index = False,
                                       convert_col_to_lower = False)
        
        df_weight = pd.concat([df_weight, current_weight]).reset_index(drop=True)
        
        #df_flkp = df_flkp.fillna(method='ffill')
        #df_temp = df_temp.fillna(method='ffill')
        #df_pres = df_pres.fillna(method='ffill')
        
        
        if len(df_flkp)> 8700:
            df_flkp = df_flkp.iloc[-8700:,:]
            df_flkp = df_flkp.reset_index(drop=True)
            df_temp = df_temp.iloc[-8700:,:]
            df_temp = df_temp.reset_index(drop=True)
            df_pres = df_pres.iloc[-8700:,:]
            df_pres = df_pres.reset_index(drop=True)
            df_wash = df_wash.iloc[-8700:,:]
            df_wash = df_wash.reset_index(drop=True)
            df_qa = df_qa.iloc[-8700:,:]
            df_qa = df_qa.reset_index(drop=True)    
            df_hf = df_hf.iloc[-8700:,:]
            df_hf = df_hf.reset_index(drop=True)
            df_weight = df_weight.iloc[-8700:,:]
            df_weight = df_weight.reset_index(drop=True)
    ##### Reading historical data (not live)
    #    df_flkp = pd.read_csv('df_flkp_Mar_July_2019.zip',sep=',',header='infer',compression='zip')
    #    ####Removing constant low flow rate
    #    fl_colnames = df_flkp.columns
    #    for x in range(1,71):
    #        df_flkp[fl_colnames[x]][df_flkp[fl_colnames[x]] <0 ] = 0.9
    #        df_flkp[fl_colnames[x]][df_flkp[fl_colnames[x]] <1 ] = 0
    #    df_wash = pd.read_csv('df_wash_Mar_July_2019.zip',sep=',',header='infer',compression='zip')
    #    df_temp = pd.read_csv('df_temp_Mar_July_2019.zip',sep=',',header='infer',compression='zip')
    #    df_temp.rename(columns={'Unnamed: 0':'datetime'},inplace=True)
    #    df_temp['datetime'] = df_flkp['datetime']
    #    df_pres = pd.read_csv('df_pres_Mar_July_2019.zip',sep=',',header='infer',compression='zip')
    #    df_qa = pd.read_csv('df_qa_Mar_July_2019.zip',sep=',',header='infer',compression='zip')
    #    df_hf = pd.read_csv('df_hf_Mar_July_2019.zip',sep=',',header='infer',compression='zip')
    #    df_flkp['datetime'] = pd.to_datetime(df_flkp['datetime'],format = '%Y-%m-%d %H:%M:%S')
    #    df_wash['datetime'] = pd.to_datetime(df_wash['datetime'],format = '%Y-%m-%d %H:%M:%S')       
    #    df_wash = df_wash.fillna(method='ffill')
    #    df_weight = pd.read_csv('df_weight_Mar_July_2019.zip',sep=',',header='infer',compression='zip')
        
        
    #    def func(x):
    #        try:
    #            return float(x)
    #        except ValueError:
    #            return np.nan
    #    for x in range(1,43):
    #        df_temp[df_temp.columns[x]] = df_temp[df_temp.columns[x]].apply(func)
    #    
        
        #### Organizing data
        print('Organizing data')
        pd.options.mode.chained_assignment = None
        df_fr = get_fr(df_wash)
        imp = get_imp(df_flkp,df_temp,df_pres)
        hbl = get_hbl(df_flkp,df_temp,df_pres)
        hwl = get_hwl(df_flkp,df_temp,df_pres,df_qa,df_weight)
        dpf = get_dpf(df_flkp,df_temp,df_pres,df_hf)
        ddf = get_ddf(df_flkp,df_temp,df_pres)
        ddf['ddf_stamp'] = pd.to_datetime(ddf['ddf_stamp'],format = '%Y-%m-%d %H:%M:%S')
        
        cycle_events = allign_cycles(imp,hbl,hwl,dpf,ddf)
        
        mltable = get_mltable(cycle_events,df_fr,df_flkp)
        print('...DONE...')
        
        ##################################
    
        
    
        print('Predicting')
    
    
    ##### creating a historical table for kappa prediction validation
        try:
            mltable_2
        except NameError:
            mltable_2 = mltable.copy()
    
    #### forward predict.py for forward prediction
    #### To predict on the first digester on mltable only
        mltable = mltable.sort_values(by='discharge time',ascending =False).reset_index(drop=True)
        mltable, mltable_2 = forward_predict(mltable,mltable_2)
        mltable = mltable.sort_values(by='discharge time',ascending =False).reset_index(drop=True)
        mltable['cook index'] = mltable.index
        
        print('Prediction Done')
        
        ######################################################################################################

            
        if mltable_2['dgtr_no'][0] == mltable['dgtr_no'][0]:
            pass
        else:
            mltable_2 = mltable_2.append(mltable.iloc[0]).sort_values(by='discharge time',ascending =False).reset_index(drop=True).reset_index(drop=True)
        
        ### Appending newest lag time & kappa  after lag time
        mltable_2.loc[:len(mltable)-1,'pre o2 kappa'] = mltable['pre o2 kappa'].values
        mltable_2.loc[:len(mltable)-1,'kappa_lag_time'] = mltable['kappa_lag_time'].values
        
        
        #### Resetting cook index
        mltable_2['cook index'] = mltable_2.index
        
        #### Pushing forward prediction 1 row up
        mltable_3 = mltable_2[['discharge time','dgtr_no','pre o2 kappa','syn_pred_b','forward_pred_b']].copy()
        
        dgtrlist = []
        for num in ['01','02','03','04','05','06','07','08','09','10','11','12','13','14']:
            temp_holder = mltable_3[mltable_3['dgtr_no']== num]
            temp_holder[['discharge time','pre o2 kappa']] = temp_holder[['discharge time','pre o2 kappa']].shift(periods=1)
            dgtrlist.append(temp_holder)
        mltable_3 = pd.concat(dgtrlist)
        mltable_3 = mltable_3.sort_index()
        
        
        #### Sort date descending, NaT at top
        mltable_3['abs_diff'] = abs(mltable_3['pre o2 kappa'] - mltable_3['syn_pred_b'])
        error_mae = mltable_3[pd.notnull(mltable_3['abs_diff'])]['abs_diff'].mean()
        error_std = mltable_3[pd.notnull(mltable_3['abs_diff'])]['abs_diff'].std()
        df_error = pd.DataFrame(data={'mae':[error_mae],'std':[error_std]})
       
       
       
       
       
        #### Threshold for returning warning
        
        print('getting outliers')
        
        df = mltable_2[out_predictors].copy()
        df[out_predictors] = StandardScaler().fit_transform(mltable_2[out_predictors].fillna(mltable_2[out_predictors].mean()))
        
        
        df_named = df.copy()
        df_named.columns = df_named.columns.map(var_cols,df_named.columns)
    
    #    cols = float_col +', '
        cols = [item + ', ' for item in df_named.columns]
        cols = list(cols)
    #    cols.append('') ---- needed if threshold is one of the column in df
        
        mltable_2['Warning'] = ''
        fframe = pd.DataFrame()
        
        for threshold in [-4,-3,2,3]:
            rowsind = []
            frame =pd.DataFrame()
            for rows in range(len(mltable_2)):
                rowsvar = []
                if (mltable_2['pre o2 kappa'][rows]<16.4) or ( mltable_2['pre o2 kappa'][rows]>17.6) :
                    rowsind.append(rows)
                    mltable_2['Warning'][rows] = 'Warning'
                    
                    temp = df_named.iloc[rows,:]
                    if threshold == -4:
                        s = np.where(temp.lt(threshold + 1,0),[cols],'')
                    elif threshold == 3:
                        s = np.where(temp.gt(threshold,0),[cols],'')                    
                    else:
                        boolval = functools.reduce(lambda x,y: x & y,[temp.gt(threshold,0),temp.lt(threshold+1,0)])
                        s = np.where(boolval,[cols],'')
                    a = pd.Series([''.join(x).strip(', ') for x in s])
    #                print(rows)
    
                    rowsvar.append(a[0])
    #                indcol = a[0].split(", ")
    #                if indcol[0] == '':
    #                    indcol[0] = 0
    #                frame = frame.append([indcol])
    #                print(functools.reduce(lambda x,y: x& y,[temp.lt(threshold,0),temp.lt(threshold,0)]))
                    frame = frame.append([rowsvar])
            frame['cook index'] = rowsind
            frame = frame[['cook index'] + [c for c in frame if c not in ['cook index']]]
            frame.columns = ['cook index', threshold]
            
            if fframe.empty:
                fframe = frame
            else:
                fframe = pd.merge(fframe,frame,left_on='cook index',right_on='cook index', how='outer')
        fframe.columns = ['cook index','-3 sigma - Outliers','-2 sigma - Cautions','+2 sigma - Cautions','+3 sigma - Outliers']
        
    #    mltable_2['dgtr_no_2'] = mltable_2.index.get_level_values('dgtr_no')
        fframe = pd.merge(fframe,mltable_2[['cook index','dgtr_no']],how='left',on='cook index')
        
        #############################################################################################################
        
        print('Getting outliers Done')
        
        
        ####Static ref table
    
        df_flkp_tag_list_ref = ['Impregnation Flow Rate']*14 \
        + ['Hot Black Liqour Flow Rate']*14 \
        + ['White Liquor Flow Rate']*14 \
        + ['Displacement Flow Rate']*14 \
        + ['Discharge Dilution Flow Rate']*14 \
        + ['Kappa Number'] \
        + ['Blow Tank Level'] \
        + ['HBL efficiency']*14 
        
        temp_tag_list_ref = ['Digester Top Temperature']*14 \
        +['Digester Mid Temperature']*14 \
        +['Digester Bottom Temperature']*14
        
        pres_tag_list_ref = ['Digester pressure']*14
        ## Tags for QA lab data. In sequence, TAA, TTA, Sulphidity,Caustisizer Eff., TSS, SO4
    
        white_liquor_qa_tag_list_ref = ['Active Alkali','Total Titratable Alkali','Sulphidity','Cauticizing Efficiency','SO4','Total Suspended Solid','AA Charge']
        
        dig_weight_tag_list_ref = ['Digester Weight after Chip Filling']*14
        
        h_factor_tag_list_ref = ['H-factor']*14
        
        minimum_list = []    
        maximum_list = []                     
        for i in df_flkp.columns[1:]:
            minimum_list.append(df_flkp[i].min())
            maximum_list.append(df_flkp[i].max())
            
        for i in df_temp.columns[1:]:
            minimum_list.append(df_temp[i].min())
            maximum_list.append(df_temp[i].max())
            
        for i in df_pres.columns[1:]:
            minimum_list.append(df_pres[i].min())
            maximum_list.append(df_pres[i].max())
            
        for i in df_qa.columns[1:8]:
            minimum_list.append(df_qa[i].min())
            maximum_list.append(df_qa[i].max())  
        #### Temporary exclude aa charge and hwl set point    
        for i in df_weight.columns[1:]:
            minimum_list.append(min(i for i in df_weight[i] if i>=0))
            maximum_list.append(df_weight[i].max()) 
            
        for i in df_hf.columns[1:]:
            minimum_list.append(df_hf[i].min())
            maximum_list.append(df_hf[i].max())        
            
        uom = ['Litres per second']*70 + ['Kappa number',]+['%']*15+['Degree Celsius']*42+['Pa']*14+['g/L','g/L','%','%','g/L','mg/L','%']+['ton']*14+['-']*14
        
        
        df_ref = pd.DataFrame(data={"Pi tags":df_flkp_tag_list+temp_tag_list+pres_tag_list+white_liquor_qa_tag_list[:-1]+dig_weight_tag_list+h_factor_tag_list,
                                    'Name of Variables':df_flkp_tag_list_ref+temp_tag_list_ref+pres_tag_list_ref+white_liquor_qa_tag_list_ref+dig_weight_tag_list_ref+h_factor_tag_list_ref,
                                    'Unit of Measure':uom,
                                    "Min Value":minimum_list,"Max Value":maximum_list})
        
    #    
    #    ####Dropping the seconds in datetime so that it display nicely in superset
        mltable_2['discharge time'] = mltable_2['discharge time'].values.astype('<M8[m]')
    #    df_flkp['datetime_nosec'] = df_flkp['datetime'].values.astype('<M8[m]')   #### Cannot change datetie column as it will interfere with datetime checking each time script is run
         
        mltable_2.to_sql('dashboard_v3',engine, if_exists="replace")
        mltable_3.to_sql('forward_prediction_valid',engine, if_exists="replace")
    #    report.to_sql('accuracy_report',engine, if_exists="replace")
        fframe.to_sql('outliers_db3',engine, if_exists="replace")
        df_flkp[["datetime","RPL.422AT2013B:value"]].to_sql('kappa_series',engine, if_exists="replace")
        df_error.to_sql('error_report',engine, if_exists = "replace")
        df_ref.to_sql('UoM_ref',engine, if_exists = "replace")
        print('written to sql: dashboard_v3, forward_prediction_valid, outliers_db3,kappa_series')
    print("Execution ended," + datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    

refresh_data()
scheduler = BlockingScheduler()
scheduler.add_job( refresh_data,'interval',minutes=5)
scheduler.start()
     