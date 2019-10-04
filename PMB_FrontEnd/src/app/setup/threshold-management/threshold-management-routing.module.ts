import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductionConfigurationComponent } from './production-configuration/production-configuration.component';
import { ConsumptionConfigurationComponent } from './consumption-configuration/consumption-configuration.component';
import { AuthGuard } from 'src/app/core/auth/auth-guard';
const routes: Routes = [
  {
    path: "",
    redirectTo: "production",
    pathMatch: "full"
  },
  {
    path: "production",
    component: ProductionConfigurationComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard]
  },
  {
    path: "consumption",
    component: ConsumptionConfigurationComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ThresholdManagementRoutingModule { }