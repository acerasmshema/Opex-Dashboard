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
  ];;

  static maintanenceDaysColumn = [
    { field: 'id', header: 'Id' },
    { field: 'maintainceDates', header: 'Date' },
    { field: 'remarks', header: 'Remarks' }
  ];

  static annotationsCols = [
    { field: 'annotationDate', header: 'Date' },
    { field: 'userId', header: 'User' },
    { field: 'processLines', header: 'Process Line' },
    { field: 'description', header: 'Description' }
  ];
}