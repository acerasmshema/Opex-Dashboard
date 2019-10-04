export class MasterData {
  static country = [
    {
      "countryId": "1",
      "countryName": "Indonesia"
    }];

  static dashboardFrequencies = [
    { name: 'Daily', code: '0' },
    { name: 'Monthly', code: '1' },
    { name: 'Quarterly', code: '2' },
    { name: 'Yearly', code: '3' }
  ];

  static maintanenceDaysColumn = [
    { field: 'maintainceDates', header: 'Date' },
    { field: 'remarks', header: 'Remarks' }
  ];

  static annotationsCols = [
    { field: 'annotationDate', header: 'Date' },
    { field: 'userId', header: 'User' },
    { field: 'processLines', header: 'Process Line' },
    { field: 'description', header: 'Description' }
  ];

  static userDetailCols = [
    { field: 'username', header: 'Username' },
    { field: 'firstName', header: 'First Name' },
    { field: 'lastName', header: 'Last Name' },
    { field: 'email', header: 'Email' },
    { field: 'millRoleSortName', header: 'Role' },
    { field: 'active', header: 'Active' }
  ];

  static campaignCols = [
    { field: 'campaignName', header: 'Campaign Name', width: "20%", showEditButton: false },
    { field: 'buType', header: 'Business Unit', width: "13%", showEditButton: false },
    { field: 'date', header: 'Start Date - End Date', width: "18%", showEditButton: false },
    { field: 'productionConfig', header: 'Production Configuration', width: "16%", showEditButton: true },
    { field: 'consumptionConfig', header: 'Consumption Configuration', width: "17%", showEditButton:true },
    { field: 'active', header: 'Active', width: "8%", showEditButton: false },
    { field: 'action', header: 'Action', width: "8%", showEditButton: true },
  ];

  static userRoleCols = [
    { field: 'roleName', header: 'Role Name', width: "25%" },
    { field: 'description', header: 'Description', width: "54%" },
    { field: 'active', header: 'Active', width: "7%" },
  ];
}