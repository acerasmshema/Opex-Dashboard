import { Component, OnInit } from '@angular/core';
import { UserDetail } from './user-detail.model';
import { MessageService } from 'primeng/primeng';
import { StatusService } from 'src/app/shared/service/status.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
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

  users: UserDetail[] = [];

  constructor(private messageService: MessageService,
               private statusService: StatusService) { }

  ngOnInit() {
    this.onGetUserList();
  }

  onGetUserList() {
    const millId = this.statusService.common.selectedMill.millId; // request URL

    //Response
    for (let index = 1; index < 30; index++) {
      let userDetail = new UserDetail();
      userDetail.userId = index;
     
      if (index < 4) {
        userDetail.role = "Admin";
        userDetail.isActive = true;
      }
      else {
        userDetail.role = "Department Head";
        userDetail.isActive = false;
      }
     
      userDetail.createdDate = "03-09-2019"
      userDetail.username = "Sahil" + index;
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

  onCancel(rUser: UserDetail) {
    const userDetail = this.users.find((user) => user.userId === rUser.userId)
    userDetail.isReadOnly = false;
    console.log(rUser.firstName)
    console.log(userDetail.firstName)
    
  }

  onCreateUser() {
    const data = {
      dialogName: "addUser",
    }
    this.statusService.dialogSubject.next(data);
  }

}
