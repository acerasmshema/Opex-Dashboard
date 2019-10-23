import { Component, OnInit } from '@angular/core';
import { CampaignModel } from './campaign-model';
import { StatusService } from 'src/app/shared/service/status.service';
import { SidebarRequest } from 'src/app/core/sidebar/sidebar-request';
import { MasterData } from 'src/app/shared/constant/MasterData';

@Component({
  selector: 'app-campaign-management',
  templateUrl: './campaign-management.component.html',
  styleUrls: ['./campaign-management.component.scss']
})
export class CampaignManagementComponent implements OnInit {

  campaigns: CampaignModel[] = [];

  cols = MasterData.campaignCols;


  constructor(private statusService: StatusService) { }

  ngOnInit() {
    document.getElementById("select_mill").style.display = "block";
    let campaignModel = new CampaignModel();
    campaignModel.campaignName = '2019 Campaign';
    campaignModel.buType = "Pulp";
    campaignModel.startDate = "01/0/2019";
    campaignModel.endDate = "31/12/2019"; 
    campaignModel.active = true;
    campaignModel.createdBy = this.statusService.common.userDetail.username;
    campaignModel.updatedBy = this.statusService.common.userDetail.username;
    this.campaigns.push(campaignModel);

    let sidebarRequest = new SidebarRequest();
    sidebarRequest.showSidebar = false;
    sidebarRequest.type = "user-management";
    this.statusService.sidebarSubject.next(sidebarRequest);
    
    for (let index = 1; index < 15; index++) {
      let campaignModel = new CampaignModel();
      campaignModel.campaignName = 'Sahil' + index;
      campaignModel.buType = "Pulp";
      campaignModel.startDate = "01/09/2019";
      campaignModel.endDate = "01/12/2019"; 
      campaignModel.active = true;
      campaignModel.createdBy = this.statusService.common.userDetail.username;
      campaignModel.updatedBy = this.statusService.common.userDetail.username;
      this.campaigns.push(campaignModel);
    }
  }

  onCreateCampaign() {
    let campaignModel = new CampaignModel();
    campaignModel.campaignName = '';
    campaignModel.active = true;
    campaignModel.startDate = "";
    campaignModel.endDate = "";
    campaignModel.createdBy = this.statusService.common.userDetail.username;
    campaignModel.updatedBy = this.statusService.common.userDetail.username;
    campaignModel.operation = "Add";

    const data = {
      dialogName: "campaign",
      campaign: campaignModel
    }
    this.statusService.dialogSubject.next(data);
  }

  onEditCampaign(campaignId: string) {
    const campaign = this.campaigns.find((campaign) => campaign.campaignId === campaignId)
    campaign.operation = "Edit";

    const data = {
      dialogName: "campaign",
      campaign: campaign
    }
    this.statusService.dialogSubject.next(data);
  }

}
