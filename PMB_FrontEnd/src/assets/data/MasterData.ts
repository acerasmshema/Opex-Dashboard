export const country = [
    {
        "countryId": "1",
        "countryName": "Indonesia"
    }];

export const mill = [
    {
        "millId": "1",
        "millName": "Kerinci"
    }];

export const businessUnit = [
    {
        "businessUnitId": "1",
        "businessUnitName": "Pulp"
    }];

export const processLines = [
    { header: 'FL1', field: 'fl1' },
    { header: 'FL2', field: 'fl2' },
    { header: 'FL3', field: 'fl3' },
    { header: 'PCD', field: 'pcd' },
    { header: 'PD1', field: 'pd1' },
    { header: 'PD2', field: 'pd2' },
    { header: 'PD3', field: 'pd3' },
    { header: 'PD4', field: 'pd4' }
  ];

export const frequencies = [
    { name: 'Daily', code: '0' },
    { name: 'Monthly', code: '1' },
    { name: 'Quarterly', code: '2' },
    { name: 'Yearly', code: '3' }
  ];

        export const maintanenceDaysColumn = [
        { field: 'id', header: 'Id' },
        { field: 'maintainceDates', header: 'Date' },
        { field: 'remarks', header: 'Remarks' }
      ];

      export const processLineColumns = [
        { field: 'date', header: 'Date' },
        { field: 'fl1', header: 'FL1' },
        { field: 'fl2', header: 'FL2' },
        { field: 'fl3', header: 'FL3' },
        { field: 'pcd', header: 'PCD' },
        { field: 'pd1', header: 'PD1' },
        { field: 'pd2', header: 'PD2' },
        { field: 'pd3', header: 'PD3' },
        { field: 'pd4', header: 'PD4' }
      ];

      export const  annotationsCols =  [
        { field: 'annotationDate', header: 'Date' },
        { field: 'userId', header: 'User' },
        { field: 'processLines', header: 'Process Line' },
        { field: 'description', header: 'Description' }
      ];