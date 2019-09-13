import { NgModule } from '@angular/core';
import { CoreModule } from '../core/core.module';
import { HttpClientModule } from '@angular/common/http';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { PrimeNgModule } from '../shared/primeng-modules';
import { UserManagementComponent } from './user-management.component';
import { UserManagementRoutingModule } from './user-management-routing.module';
import { CommonModule } from '@angular/common';
import { UserRoleComponent } from './user-role/user-role.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [UserManagementComponent, UserDetailComponent, UserRoleComponent],
  imports: [
    CommonModule,
    CoreModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    UserManagementRoutingModule,
    PrimeNgModule,
  ]
})
export class UserManagementModule { }
