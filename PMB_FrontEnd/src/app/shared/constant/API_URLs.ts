import { environment } from "../../../environments/environment";

export class API_URL {

    static BASE_API_URL_v1 = environment.API_URL + '/restCall/v1';

    static BASE_API_URL_v2 = environment.API_URL + '/restCall/v2';

    static apiURLs = {
        ALL_MILLS_URL: API_URL.BASE_API_URL_v1 + '/location/all_mills',
        ALL_BU_TYPE_URL: API_URL.BASE_API_URL_v1 + '/location/get_all_bu_type',
        PROCESS_LINES_URL: API_URL.BASE_API_URL_v1 + '/yesterday/total_process_line',
        PROJECTED_DATA_URL: API_URL.BASE_API_URL_v1 + '/process_line/projected_target_details',
        YTD_ALL_PROCESS_LINES_URL: API_URL.BASE_API_URL_v1 + '/yesterday/all_process_lines',
        KPI_PULP_AREA_URL: API_URL.BASE_API_URL_v1 + '/date_range/daily_kpi_pulp_area',
        KPI_PULP_URL: API_URL.BASE_API_URL_v1 + '/date_range/daily_kpi_pulp',
        YTD_PROCESS_LINE_URL: API_URL.BASE_API_URL_v2 + '/yesterday/ytd_process_line',
        YTD_PROCESS_LINE_TARGET_URL: API_URL.BASE_API_URL_v1 + '/yesterday/ytd_target_process_line',
        DR_ALL_PROCESS_LINES_URL: API_URL.BASE_API_URL_v1 + '/date_range/all_process_lines',
        ALL_PROCESS_LINES_TARGET_URL: API_URL.BASE_API_URL_v1 + '/date_range/all_process_lines_target',
        DR_SELECTED_PROCESS_LINES_URL: API_URL.BASE_API_URL_v1 + '/date_range/selected_process_lines',
        DOWNLOAD_DATA_GRID_URL: API_URL.BASE_API_URL_v1 + '/date_range/selected_process_lines_grid_data',
        LOGIN_URL: API_URL.BASE_API_URL_v1 + '/user_info/login',
        PROCESS_LINES_BY_KPI_ID_URL: API_URL.BASE_API_URL_v1 + '/kpi_category/get_process_lines',
        LOGOUT_URL: API_URL.BASE_API_URL_v1 + '/user_info/logout',
        KPI_TYPE_URL: API_URL.BASE_API_URL_v1 + '/kpi_category/get_kpi_type',
        CONSUMPTION_API_URL: API_URL.BASE_API_URL_v1 + '/kpi_category/selected_kpi_process_lines',
        CONSUMPTION_GRID_API_URL: API_URL.BASE_API_URL_v1 + '/kpi_category/yesterday_all_process_lines_data',

        CONSUMPTION_API_GRID_URL: API_URL.BASE_API_URL_v1 + '/kpi_category/selected_kpi_grid_data',
        SAVE_ANNOTATION_URL: API_URL.BASE_API_URL_v1 + '/kpi_annotation/save_annotation',
        FIND_ANNOTATION_URL: API_URL.BASE_API_URL_v1 + '/kpi_annotation/get_annotation',
        DEL_ANNOTATION_URL: API_URL.BASE_API_URL_v1 + '/kpi_annotation/delete_annotation',

        GET_MAINTENANCE_DAYS_URL: API_URL.BASE_API_URL_v1 + '/maintenance_days/get_maintenance_days',
        SAVE_MAINTENANCE_DAYS_URL: API_URL.BASE_API_URL_v1 + '/maintenance_days/save_maintenance_days',
        DELETE_MAINTENANCE_DAYS_URL: API_URL.BASE_API_URL_v1 + '/maintenance_days/delete_maintenance_days',
        UPDATE_MAINTENANCE_DAYS_REMARKS: API_URL.BASE_API_URL_v1 + '/maintenance_days/update_maintenance_days_remarks',

        SAVE_TARGET_DAYS_URL: API_URL.BASE_API_URL_v1 + '/kpi_category/save_target_days',
        ANNOTATION_DATES_URL: API_URL.BASE_API_URL_v1 + '/kpi_annotation/get_annotation_date',
        ALL_PROCESS_LINES_URL: API_URL.BASE_API_URL_v1 + '/process_line/get_all_process_lines',
        BENCHMARK_FILTER_URL: API_URL.BASE_API_URL_v1 + '/benchmarking/get_selected_data',
    };

    static user_api_URLs = {
        ALL_COUNTRY: API_URL.BASE_API_URL_v1 + '/countries',
        ALL_DEPARTMENT: API_URL.BASE_API_URL_v1 + '/departments',
        ALL_USER: API_URL.BASE_API_URL_v1 + '/users',
        ALL_USER_ROLE: API_URL.BASE_API_URL_v1 + '/roles',
        ADD_USER_ROLE: API_URL.BASE_API_URL_v1 + '/create_user_role',
        UPDATE_USER_ROLE: API_URL.BASE_API_URL_v1 + '/update_user_role',
        CREATE_USER: API_URL.BASE_API_URL_v1 + '/create_user',
        UPDATE_USER: API_URL.BASE_API_URL_v1 + '/update_user',
        VALIDATE_EMAIL: API_URL.BASE_API_URL_v1 + '/validate_email',
    }
    
}