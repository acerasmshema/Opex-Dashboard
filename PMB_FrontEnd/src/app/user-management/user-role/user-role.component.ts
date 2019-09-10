import { Component, OnInit } from '@angular/core';
import { UserRole } from './user-role.model';

@Component({
  selector: 'app-user-role',
  templateUrl: './user-role.component.html',
  styleUrls: ['./user-role.component.scss']
})
export class UserRoleComponent implements OnInit {

  roles: any = [];

  constructor() { }

  ngOnInit() {
    this.onGetUserRoles();
  }

  onGetUserRoles() {
    let roles = ["Admin", "Deparment Head", "BCID", "MillOps"];
    for (let index = 1; index < 5; index++) {
      let userRole = new UserRole();
      userRole.userRoleId = index;
      userRole.sNo = index;
      userRole.roleName = roles[index - 1];
      userRole.status = true;
      userRole.isReadOnly = true;
      this.roles.push(userRole);
    }
  }

  onEdit(userRoleId: number) {
    const userRole = this.roles.find((userRole) => userRole.userRoleId === userRoleId)
    userRole.isReadOnly = false;
  }

  onCancel(userRole: UserRole) {
    userRole.isReadOnly = true;
  }

  onSave(userRole: UserRole) {
    userRole.isReadOnly = true;
  }

}
