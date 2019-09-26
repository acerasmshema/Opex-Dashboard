import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './core/layout/layout.component';
import { LoginComponent } from './profile/login/login.component';
import { AuthGuard } from './core/auth/auth-guard';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: "",
    redirectTo: "home",
    pathMatch: "full"
  },
  {
    path: "",
    component: LayoutComponent,
    children: [
      {
        path: "home",
        pathMatch: 'full',
        loadChildren: './dashboard/dashboard.module#DashboardModule',
        canActivate: [AuthGuard]
      },
      {
        path: "benchmark",
        pathMatch: 'full',
        loadChildren: './benchmark/benchmark.module#BenchmarkModule',
        canActivate: [AuthGuard]
      },
      {
        path: "kappaAnalytics",
        pathMatch: 'full',
        loadChildren: './kappa-analytics/kappa-analytics.module#kappaAnalyticsModule',
        canActivate: [AuthGuard]
      },
      {
        path: "user",
        pathMatch: 'full',
        loadChildren: './user-management/user-management.module#UserManagementModule',
        canActivate: [AuthGuard]
      },
      {
        path: "profile",
        pathMatch: 'full',
        loadChildren: './profile/profile.module#ProfileModule',
        canActivate: [AuthGuard]
      }
    ]
  },
  { path: "**",redirectTo:"home"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }