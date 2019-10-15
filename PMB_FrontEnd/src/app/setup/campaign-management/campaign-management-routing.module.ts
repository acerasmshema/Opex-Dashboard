import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CampaignManagementComponent } from './campaign-management.component';
const routes: Routes = [
  {
    path: '',
    component: CampaignManagementComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CampaignManagementRoutingModule { }