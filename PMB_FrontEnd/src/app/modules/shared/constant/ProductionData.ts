export class ProductionData {
  static cols = [
    { code: 'date', name: 'Date' },
    { code: 'fl1', name: 'FL1' },
    { code: 'fl2', name: 'FL2' },
    { code: 'fl3', name: 'FL3' },
    { code: 'pcd', name: 'PCD' },
    { code: 'pd1', name: 'PD1' },
    { code: 'pd2', name: 'PD2' },
    { code: 'pd3', name: 'PD3' },
    { code: 'pd4', name: 'PD4' }
  ];

static annotationsCols = [
  { field: 'date', header: 'Date' },
  { field: 'user', header: 'User' },
  { field: 'processLine', header: 'Process Line' },
  { field: 'description', header: 'Description' }
];

static frequencies = [
  { name: 'Daily', code: 'dl' },
  { name: 'Monthly', code: 'mn' },
  { name: 'Quarterly', code: 'qt' },
  { name: 'Yearly', code: 'yr' }
];

static processLines = [
  { name: 'FL1', code: 'fl1' },
  { name: 'FL2', code: 'fl2' },
  { name: 'FL3', code: 'fl3' },
  { name: 'PCD', code: 'pcd' },
  { name: 'PD1', code: 'pd1' },
  { name: 'PD2', code: 'pd2' },
  { name: 'PD3', code: 'pd3' },
  { name: 'PD4', code: 'pd4' }
]
};