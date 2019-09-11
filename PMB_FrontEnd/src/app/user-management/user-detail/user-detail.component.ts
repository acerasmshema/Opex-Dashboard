import { Component, OnInit } from '@angular/core';
import { UserDetail } from './user-detail.model';
import { MessageService } from 'primeng/primeng';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserDetailService } from './user-detail.service';

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
    { field: 'role', header: 'Role' },
    { field: 'isActive', header: 'Status' }
  ];

  public users: UserDetail[] = [];
  private editUserDetail: UserDetail;

  constructor(private messageService: MessageService,
    private userDetailService: UserDetailService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.userDetailService.getUserDetailList(this.users);
  }

  onEdit(userId: number) {
    const userDetail = this.users.find((user) => user.userId === userId)
    userDetail.isReadOnly = true;

    this.editUserDetail = new UserDetail();
    this.editUserDetail.firstName = userDetail.firstName;
    this.editUserDetail.lastName = userDetail.lastName;
  }

  onSave(userDetail: UserDetail) {
    this.userDetailService.saveUserDetail(userDetail, this.users);
    this.editUserDetail = null;
  }

  onCancel(userDetail: UserDetail) {
    userDetail.isReadOnly = false;
    userDetail.firstName = this.editUserDetail.firstName;
    userDetail.lastName = this.editUserDetail.lastName;
  }

  onCreateUser() {
    const data = {
      dialogName: "addUser",
    }
    this.statusService.dialogSubject.next(data);
  }

}
