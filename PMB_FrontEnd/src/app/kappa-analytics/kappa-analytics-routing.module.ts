import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { KappaNumberPredictionComponent } from './kappa-number-prediction.component';
const routes: Routes = [
  {
    path: '',
    component: KappaNumberPredictionComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KappaNumberPredictionRoutingModule { }