import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ProfileEditService } from './profile-edit.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserDetail } from 'src/app/setup/user-management/user-detail/user-detail.model';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { ValidationService } from 'src/app/shared/service/validation/validation.service';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { MessageService } from 'primeng/primeng';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss'],
  providers: [ProfileEditService]
})
export class ProfileEditComponent implements OnInit {

  userDetailForm: FormGroup;
  private userDetail: UserDetail;

  constructor(private profileEditService: ProfileEditService,
    private commonService: CommonService,
    private formBuilder: FormBuilder,
    private validationService: ValidationService,
    private messageService: MessageService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.onGetProfile();
  }

  onCancel() {
    this.createUserDetailForm();
  }

  onEditProfileSave() {
    if (!this.userDetailForm.valid)
      return;
    this.profileEditService.saveProfile(this.userDetailForm);
  }

  onCountryChange(countryId: string) {
    if (countryId !== '') {
      let country = this.statusService.common.countryList.find(country => country.countryId === countryId);
      this.userDetailForm.controls.country.setValue(country);
    } else {
      this.userDetailForm.controls.country.setValue(null);
    }
  }

  onDepartmentChange(departmentId: string) {
    if (departmentId !== '') {
      const department = this.statusService.common.departmentList.find(department => department.departmentId === departmentId);
      this.userDetailForm.controls.department.setValue(department);
    } else {
      this.userDetailForm.controls.department.setValue(null);
    }
  }

  createUserDetailForm() {
    this.userDetailForm = this.formBuilder.group({
      firstName: new FormControl(this.userDetail.firstName, [Validators.required]),
      lastName: new FormControl(this.userDetail.lastName, [Validators.required]),
      validateEmail: new FormControl(this.userDetail.email),
      email: new FormControl(this.userDetail.email, { validators: [Validators.required, Validators.email], asyncValidators: [this.validationService.forbiddenEmail.bind(this)], updateOn: 'blur' }),
      phone: new FormControl(this.userDetail.phone),
      address: new FormControl(this.userDetail.address),
      country: new FormControl(this.userDetail.country),
      countryList: this.formBuilder.array([]),
      department: new FormControl(this.userDetail.department),
      departmentList: this.formBuilder.array([])
    });

    this.userDetailForm.controls.email.valueChanges.
      subscribe((event) => {
        if (event !== null)
          this.userDetailForm.get('email').setValue(event.toLowerCase().trim(), { emitEvent: false });
      });

    this.commonService.getAllCountry(this.userDetailForm);
    this.commonService.getAllDepartment(this.userDetailForm);
  }

  onGetProfile() {
    this.profileEditService.getProfile().
      subscribe(
        (userDetail: UserDetail) => {
          this.userDetail = userDetail;
          this.createUserDetailForm();
        },
        (error: any) => {
          this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
        }
      );
  }

  onInputChange(value: any) {
    let formControl: any = this.userDetailForm.get(value);
    this.validationService.trimValue(formControl);
  }

}
