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

  static processLineTargetCols = [
    { field: 'buType', header: 'Business Type', showInputBox: true },
    { field: 'processLine', header: 'Process Line', showInputBox: true },
    { field: 'threshold', header: 'Threshold', showInputBox: true },
    { field: 'maximum', header: 'Maximum', showInputBox: true },
    { field: 'startDate', header: 'Start Date', showInputBox: true },
    { field: 'endDate', header: 'End Date', showInputBox: true }
  ];

  static productionThresholdCols = [
    { field: 'buType', header: 'Business Type', showInputBox: true },
    { field: 'threshold', header: 'Threshold', showInputBox: true },
    { field: 'maximum', header: 'Maximum', showInputBox: true },
    { field: 'startDate', header: 'Start Date', showInputBox: true },
    { field: 'endDate', header: 'End Date', showInputBox: true }
  ];

  static annualConfigCols = [
    { field: 'buType', header: 'Business Type', showInputBox: true },
    { field: 'year', header: 'Year', showInputBox: true },
    { field: 'workingDays', header: 'Working Days', showInputBox: true },
    { field: 'target', header: 'Annual Target', showInputBox: true }
  ];

  static consumptionThresholdCols = [
    { field: 'processLine', header: 'Process Line', showInputBox: true },
    { field: 'threshold', header: 'Threshold', showInputBox: true },
    { field: 'startDate', header: 'Start Date', showInputBox: true },
    { field: 'endDate', header: 'End Date', showInputBox: true }
  ];

  static campaignCols = [
    { field: 'campaignName', header: 'Campaign Name', width: "20%", showEditButton: false },
    { field: 'buType', header: 'Business Unit', width: "13%", showEditButton: false },
    { field: 'date', header: 'Start Date - End Date', width: "18%", showEditButton: false },
    { field: 'active', header: 'Active', width: "9%", showEditButton: false },
    { field: 'productionConfig', header: 'Production Configuration', width: "16%", showEditButton: true },
    { field: 'processLineConfig', header: 'Process Line Configuration', width: "16%", showEditButton: true },
    { field: 'consumptionConfig', header: 'Consumption Configuration', width: "16%", showEditButton: true },
    { field: 'action', header: 'Action', width: "8%", showEditButton: true },
  ];

  static userRoleCols = [
    { field: 'roleName', header: 'Role Name', width: "25%" },
    { field: 'description', header: 'Description', width: "54%" },
    { field: 'active', header: 'Active', width: "7%" },
  ];
}