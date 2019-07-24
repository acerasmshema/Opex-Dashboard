import { TestBed } from '@angular/core/testing';

import { ChemicalConsumptionService } from './chemical-consumption.service';

describe('ChemicalConsumptionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ChemicalConsumptionService = TestBed.get(ChemicalConsumptionService);
    expect(service).toBeTruthy();
  });
});
