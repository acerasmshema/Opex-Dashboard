import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CampaignManagementComponent } from './campaign-management.component';
import { CoreModule } from 'src/app/core/core.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PrimeNgModule } from 'src/app/shared/primeng-modules';
import { CampaignManagementRoutingModule } from './campaign-management-routing.module';

@NgModule({
  declarations: [CampaignManagementComponent],
  imports: [
    CommonModule,
    CoreModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CampaignManagementRoutingModule,
    PrimeNgModule,
  ]
})
export class CampaignManagementModule { }
