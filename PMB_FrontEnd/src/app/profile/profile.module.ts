import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile.component';
import { SettingComponent } from './setting/setting.component';
import { PasswordComponent } from './setting/password/password.component';
import { ProfileEditComponent } from './setting/profile-edit/profile-edit.component';
import { ProfileRoutingModule } from './profile-routing.module';
import { CoreModule } from '../core/core.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PrimeNgModule } from '../shared/primeng-modules';

@NgModule({
  declarations: [ProfileComponent, SettingComponent, PasswordComponent, ProfileEditComponent],
  imports: [
    CommonModule,
    FormsModule,
    CoreModule,
    ReactiveFormsModule,
    HttpClientModule,
    ProfileRoutingModule,
    PrimeNgModule,
  ]
})
export class ProfileModule { }
