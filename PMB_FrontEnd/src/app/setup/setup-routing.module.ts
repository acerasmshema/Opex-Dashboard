import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../core/auth/auth-guard';
const routes: Routes = [
  
  {
    path: "user",
    pathMatch: 'full',
    loadChildren: './user-management/user-management.module#UserManagementModule',
    canActivate: [AuthGuard]
  },
  {
    path: "campaign",
    pathMatch: 'full',
  loadChildren: './campaign-management/campaign-management.module#CampaignManagementModule',
    canActivate: [AuthGuard]
  },
  {
    path: "threshold",
    loadChildren: './threshold-management/threshold-management.module#ThresholdManagementModule',
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SetupRoutingModule { }