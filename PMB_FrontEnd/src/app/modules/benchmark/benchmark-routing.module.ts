import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BenchmarkComponent } from './benchmark.component';
const routes: Routes = [
  {
    path: '',
    component: BenchmarkComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BenchmarkRoutingModule { }