import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserDetail } from './user-detail.model';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserDetailService } from './user-detail.service';
import { FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ValidationService } from 'src/app/shared/service/validation/validation.service';
import { MasterData } from 'src/app/shared/constant/MasterData';
import { ConfirmationService } from 'primeng/primeng';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss'],
})
export class UserDetailComponent implements OnInit, OnDestroy {

  cols = MasterData.userDetailCols;
  users: UserDetail[] = [];
  userDetailForm: FormGroup;
  userSubscription: Subscription;

  constructor(private userDetailService: UserDetailService,
    private validationService: ValidationService,
    private confirmationService: ConfirmationService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.userDetailService.getUserDetailList(this.users);

    this.userSubscription = this.statusService.refreshUserList.
      subscribe((isRefresh: boolean) => {
        this.users = [];
        this.userDetailService.getUserDetailList(this.users);
      })
  }

  expandUserDetail(userId: string) {
    const userDetail = this.users.find((user) => user.userId === userId);
    this.userDetailForm = this.userDetailService.createUserDetailForm(userDetail);
    this.userDetailForm.disable();
    this.userDetailForm.controls.disableSelect.setValue(true);
  }

  onEdit(userId: string) {
    this.userDetailForm.enable();
    this.userDetailForm.controls.disableSelect.setValue(false);
  }

  onCancel(userInfo: UserDetail) {
    const userDetail = this.users.find((user) => user.userId === userInfo.userId)
    this.userDetailForm = this.userDetailService.createUserDetailForm(userDetail);
    setTimeout(() => {
      this.userDetailForm.disable();
      this.userDetailForm.controls.disableSelect.setValue(true);
    }, 10);
  }

  onCreateUser() {
    const data = {
      dialogName: "addUser",
    }
    this.statusService.dialogSubject.next(data);
  }

  onAddMillRole() {
    if (this.userDetailForm.enabled) {
      this.userDetailService.addMillRole(this.userDetailForm);
    }
    else {
      return false;
    }
  }

  onDeleteMillRole(index: number) {
    if (this.userDetailForm.enabled && index > 0) {
      let millRoles: any = this.userDetailForm.controls.millRoles;
      millRoles.removeAt(index);
      let millControls = millRoles.controls;
      millControls[millControls.length - 1].value.selectedMill.enable();
      millControls[millControls.length - 1].value.selectedUserRole.enable();
    }
    else {
      return false;
    }
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

  onMillChange(millId: string, millRole: FormGroup) {
    if (!this.validationService.validateMillExist(this.userDetailForm, millId)) {
      const mill = this.statusService.common.mills.find(mill => mill.millId === millId);
      millRole.value.selectedMill.setValue(mill);
      millRole.value.millError.setValue("");
    }
    else {
      millRole.value.millError.setValue("2");
    }
  }

  onUserRoleChange(userRoleId: string, millRole: FormGroup) {
    const userRole = this.statusService.common.activeUserRoles.find(role => role.userRoleId === userRoleId);
    millRole.value.selectedUserRole.setValue(userRole);
    millRole.value.roleError.setValue("");
  }

  onUserStatusChange(status: string, userInfo: UserDetail) {
    if (this.statusService.common.userDetail.userId !== userInfo.userId) {
      this.userDetailForm.controls.active.setValue((status !== "false") ? true : false);
    }
    else if (status === 'false') {
      this.confirmationService.confirm({
        message: CommonMessage.MESSAGE.INACTIVE_USER,
        accept: () => {
          this.userDetailForm.controls.active.setValue((status !== "false") ? true : false);
        },
        reject: () => {
          let selectStatusElement: any = document.getElementById('statusSelect');
          selectStatusElement.value = "true";
        }
      });
    }
    else {
      this.userDetailForm.controls.active.setValue((status !== "false") ? true : false);
    }
  }

  onUpdateUser(userId: string) {
    if (this.userDetailForm.invalid)
      return false;

    this.userDetailService.updateUser(this.userDetailForm, this.users, userId);;
  }

  onInputChange(value: any) {
    let formControl: any = this.userDetailForm.get(value);
    this.validationService.trimValue(formControl);
  }

  ngOnDestroy() {
    this.userSubscription.unsubscribe();
  }
}