import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ProfileEditService } from './profile-edit.service';
import { StatusService } from 'src/app/shared/service/status.service';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss'],
  providers: [ProfileEditService]
})
export class ProfileEditComponent implements OnInit {

  userDetailForm: FormGroup;

  constructor(private profileEditService: ProfileEditService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.userDetailForm = null;
    this.userDetailForm = this.profileEditService.createUserDetailForm();
  }

  onCancel() {
    this.userDetailForm = this.profileEditService.createUserDetailForm();
  }

  onEditProfileSave() {
    if (!this.userDetailForm.valid)
      return;
    this.profileEditService.saveProfile(this.userDetailForm);
  }

  onCountryChange(countryName: string) {
    this.userDetailForm.controls.country.setValue(countryName);
  }

  onDepartmentChange(departmentId: string) {
    const department = this.statusService.common.departmentList.find(department => department.departmentId === departmentId);
    this.userDetailForm.controls.department.setValue(department);
  }

}
