import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './shared/login/login.component';
import { LayoutComponent } from './core/layout/layout.component';

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
        path: "",
        redirectTo: "dashboard",
        pathMatch: "full"
      },
      {
        path: "dashboard",
        pathMatch: 'full',
        loadChildren: './dashboard/dashboard.module#DashboardModule'
      },
      {
        path: "benchmark",
        pathMatch: 'full',
        loadChildren: './benchmark/benchmark.module#BenchmarkModule'
      },
      {
        path: "kappaAnalytics",
        pathMatch: 'full',
        loadChildren: './kappa-analytics/kappa-analytics.module#kappaAnalyticsModule'
      }
    ]
  },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }