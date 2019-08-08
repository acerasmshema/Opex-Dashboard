import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './modules/shared/login/login.component';
import { LayoutComponent } from './modules/core/layout/layout.component';
import { BenchmarkModule } from './modules/benchmark/benchmark.module';

const routes: Routes = [
  {
    path: "",
    redirectTo: "login",
    pathMatch: "full"
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home',
    component: LayoutComponent,
    children: [
      {
        path: "dashboard",
        pathMatch: 'full',
        loadChildren: './modules/dashboard/dashboard.module#DashboardModule'
      },
      {
        path: "benchmark",
        pathMatch: 'full',
        loadChildren: './modules/benchmark/benchmark.module#BenchmarkModule'
      }
    ]
  },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }