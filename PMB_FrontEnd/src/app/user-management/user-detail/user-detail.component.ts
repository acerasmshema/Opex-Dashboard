import { Component, OnInit } from '@angular/core';
import { UserDetail } from './user-detail.model';
import { MessageService } from 'primeng/primeng';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserDetailService } from './user-detail.service';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss'],
})
export class UserDetailComponent implements OnInit {

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

  constructor(private userDetailService: UserDetailService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.userDetailService.getUserDetailList(this.users);
  }

  expandUserDetail(userId: string) {
    const userDetail = this.users.find((user) => user.userId === userId);
    this.userDetailForm = this.userDetailService.createUserDetailForm(userDetail);
  }

  onEdit(userId: string) {
    const userDetail = this.users.find((user) => user.userId === userId)
    userDetail.isReadOnly = true;
  }

  onSave(userInfo: UserDetail) {
    const userDetail = this.users.find((user) => user.userId === userInfo.userId)
    this.userDetailService.saveUserDetail(userDetail, this.users);
    userDetail.isReadOnly = false;
  }

  onCancel(userInfo: UserDetail) {
    const userDetail = this.users.find((user) => user.userId === userInfo.userId)
    userDetail.isReadOnly = false;
    this.userDetailForm = this.userDetailService.createUserDetailForm(userDetail);
  }

  onCreateUser() {
    const data = {
      dialogName: "addUser",
    }
    this.statusService.dialogSubject.next(data);
  }

  onAddMillRole(isReadOnly: boolean) {
    if (!isReadOnly) {
      this.userDetailService.addMillRole(this.userDetailForm);
    }
    else {
      return false;
    }
  }

  onDeleteMillRole(index: number, isReadOnly: boolean) {
    if (!isReadOnly) {
      let millRoles: any = this.userDetailForm.controls.millRoles;
      millRoles.removeAt(index);
    }
    else {
      return false;
    }
  }
}
