import { Component, OnInit } from '@angular/core';
import { UserRole } from './user-role.model';
import { UserRoleService } from './user-role.service';

@Component({
  selector: 'app-user-role',
  templateUrl: './user-role.component.html',
  styleUrls: ['./user-role.component.scss']
})
export class UserRoleComponent implements OnInit {

  public userRoles: UserRole[] = [];
  private editUserRole: UserRole;

  constructor(private userRoleService: UserRoleService) { }

  ngOnInit() {
    this.userRoleService.getUserRoles(this.userRoles);
  }

  onCreateUserRole() {
    this.userRoleService.createUserRole(this.userRoles);
  }

  onEdit(userRoleId: number) {
    const userRole = this.userRoles.find((userRole) => userRole.userRoleId === userRoleId)
    userRole.isReadOnly = false;

    this.editUserRole = new UserRole();
    this.editUserRole.roleName = userRole.roleName;
    this.editUserRole.status = userRole.status;
  }

  onCancel(userRole: UserRole) {
    userRole.isReadOnly = true;
    userRole.roleName = this.editUserRole.roleName;
    userRole.status = this.editUserRole.status;
  }

  onSave(userRole: UserRole) {
    this.userRoleService.saveUserRole(userRole);
    this.editUserRole = null;
  }

}
