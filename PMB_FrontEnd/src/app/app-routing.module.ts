import { LoginComponent } from './components/login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BenchmarkSetupComponent } from './components/benchmark/benchmark-setup/benchmark-setup.component';
import { CreateBenchmarkSetComponent } from './components/benchmark/create-benchmark-set/create-benchmark-set.component'
import { SearchBenchmarkSetComponent } from './components/benchmark/search-benchmark-set/search-benchmark-set.component'
import { LayoutComponent } from './components/layout/layout.component';
import { CreateBenchmarkComponent } from './components/benchmark/create-benchmark/create-benchmark.component';
import { SearchBenchmarkComponent } from './components/benchmark/search-benchmark/search-benchmark.component';
import { TabbedPanelComponent } from './components/tabbed-panel/tabbed-panel.component';
const routes: Routes = [

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {
    path: 'home', component: LayoutComponent,
    children: [
      { path: '', component: TabbedPanelComponent },
      
      
      {
        path: 'benchmarkSetup', component: BenchmarkSetupComponent,
        children: [
          { path: 'createSet', component: CreateBenchmarkSetComponent },
          { path: 'searchSet', component: SearchBenchmarkSetComponent },
          { path: 'createBenchmark', component: CreateBenchmarkComponent },
          { path: 'searchBenchmark', component: SearchBenchmarkComponent },
        ]
      },
      {
        path: 'masterDataSetup', component: BenchmarkSetupComponent,
        children: [
          { path: 'country', component: CreateBenchmarkSetComponent },
          { path: 'location', component: CreateBenchmarkSetComponent },
          { path: 'entity', component: CreateBenchmarkSetComponent },
          { path: 'businessUnit', component: CreateBenchmarkSetComponent },
          { path: 'kpiCategory', component: CreateBenchmarkSetComponent },
          { path: 'kpi', component: CreateBenchmarkSetComponent },
          { path: 'productionLine', component: CreateBenchmarkSetComponent },
          { path: 'kpiMaintenance', component: CreateBenchmarkSetComponent },
        ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }