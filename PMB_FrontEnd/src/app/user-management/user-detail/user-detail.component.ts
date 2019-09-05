import { Component, OnInit } from '@angular/core';
import { UserDetail } from './user-detail.model';
import { MessageService } from 'primeng/primeng';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit {

  isShow: boolean = false;

  cols = [
    { field: 'firstName', header: 'First Name' },
    { field: 'lastName', header: 'Last Name' },
    { field: 'email', header: 'Email' },
    { field: 'role', header: 'Role' },
    { field: 'isActive', header: 'Status' }
  ];

  users: UserDetail[] = [];

  constructor(private messageService: MessageService) {
  }

  ngOnInit() {
    for (let index = 1; index < 30; index++) {
      let userDetail = new UserDetail();
      userDetail.userId = index;
      if (index < 4) {
        userDetail.role = 1;
        userDetail.isActive = true;
      }
      else {
        userDetail.role = 2;
        userDetail.isActive = false;
      }
      userDetail.createdDate = "03-09-2019" 
      userDetail.loginId = "Sahil" + index;
      userDetail.firstName = "Sahil" + index;
      userDetail.middleName = "Khushi";
      userDetail.address = "Baker Street " + index;
      userDetail.lastName = "Kalra" + index;
      userDetail.email = "sahil.kalra" + index + "@globallogic.com";
      userDetail.phone = "9696048000";
      userDetail.isReadOnly = false;
      userDetail.country = "India";

      this.users.push(userDetail);
    }
    this.isShow = true;
  }

  onEdit(userId: number) {
    const userDetail = this.users.find((user) => user.userId === userId)
    userDetail.isReadOnly = true;
  }

  onSave(userId: number) {
    const userDetail = this.users.find((user) => user.userId === userId)
    userDetail.isReadOnly = false;
    this.messageService.add({ severity: "success", summary: '', detail: "Updated Successfully" });
  }

  onCancel(userId: number) {
    const userDetail = this.users.find((user) => user.userId === userId)
    userDetail.isReadOnly = false;
  }

}
