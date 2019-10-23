import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { UserManagementComponent } from './user-management.component';
import { UserManagementRoutingModule } from './user-management-routing.module';
import { CommonModule } from '@angular/common';
import { UserRoleComponent } from './user-role/user-role.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from 'src/app/core/core.module';
import { PrimeNgModule } from 'src/app/shared/primeng-modules';

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
