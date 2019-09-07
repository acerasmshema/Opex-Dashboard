import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './core/layout/layout.component';
import { LoginComponent } from './profile/login/login.component';

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
      },
      {
        path: "user",
        pathMatch: 'full',
        loadChildren: './user-management/user-management.module#UserManagementModule'
      },
      {
        path: "profile",
        pathMatch: 'full',
        loadChildren: './profile/profile.module#ProfileModule'
      }
    ]
  },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }