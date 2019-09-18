import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserDetail } from './user-detail.model';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserDetailService } from './user-detail.service';
import { FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss'],
})
export class UserDetailComponent implements OnInit, OnDestroy {

  cols = [
    { field: 'username', header: 'Username' },
    { field: 'firstName', header: 'First Name' },
    { field: 'lastName', header: 'Last Name' },
    { field: 'email', header: 'Email' },
    { field: 'millRoles', header: 'Role' },
    { field: 'active', header: 'Status' }
  ];

  users: UserDetail[] = [];
  userDetailForm: FormGroup;
  userSubscription: Subscription;

  constructor(private userDetailService: UserDetailService,
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
  }

  onEdit(userId: string) {
    const userDetail = this.users.find((user) => user.userId === userId)
    this.userDetailForm.enable();
  }

  onCancel(userInfo: UserDetail) {
    const userDetail = this.users.find((user) => user.userId === userInfo.userId)
    this.userDetailForm = this.userDetailService.createUserDetailForm(userDetail);
    setTimeout(() => {
      this.userDetailForm.disable();
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
    }
    else {
      return false;
    }
  }

  onCountryChange(countryName: string) {
    this.userDetailForm.controls.country.setValue(countryName);
  }

  onDepartmentChange(departmentId: string) {
    const department = this.statusService.common.departmentList.find(department => department.departmentId === departmentId);
    this.userDetailForm.controls.department.setValue(department);
  }

  onMillChange(millId: string, millRole: FormGroup) {
    const mill = this.statusService.common.mills.find(mill => mill.millId === millId);
    millRole.value.selectedMill.setValue(mill);
  }

  onUserRoleChange(userRoleId: string, millRole: FormGroup) {
    const userRole = this.statusService.common.activeUserRoles.find(role => role.userRoleId === userRoleId);
    millRole.value.selectedUserRole.setValue(userRole);
  }

  onUserStatusChange(status: string, userInfo: UserDetail) {
    if (this.statusService.common.userDetail.userId !== userInfo.userId) {
      this.userDetailForm.controls.status.setValue(status);
    }
    else if (status === "false" && !confirm("Are you sure you want to inactivate yourself?")) {
      let selectStatusElement: any = document.getElementById('statusSelect');
      selectStatusElement.value = "true";
    }

  }

  onUpdateUser(userId: string) {
    if (this.userDetailForm.invalid)
      return false;

    const userDetail = this.users.find((user) => user.userId === userId)
    this.userDetailService.updateUser(this.userDetailForm, userDetail);
    this.userDetailForm.disable();
  }

  ngOnDestroy() {
    this.userSubscription.unsubscribe();
  }
}
