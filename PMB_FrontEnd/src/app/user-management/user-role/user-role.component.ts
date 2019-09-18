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
  private selectedUserRole: UserRole;

  cols = [
    { field: 'roleName', header: 'Role Name', width: "25%" },
    { field: 'description', header: 'Description', width: "55%" },
    { field: 'status', header: 'Status', width: "6%" },
  ];

  constructor(private userRoleService: UserRoleService) { }

  ngOnInit() {
    this.userRoleService.getUserRoles(this.userRoles);
  }

  onCreateUserRole() {
    this.userRoleService.createUserRole(this.userRoles);
  }

  onEdit(userRoleId: string) {
    const userRole = this.userRoles.find((userRole) => userRole.userRoleId === userRoleId)
    this.selectedUserRole = new UserRole();
    this.selectedUserRole.roleName = userRole.roleName;
    this.selectedUserRole.active = userRole.active;

    userRole.isEnable = true;
  }

  onCancel(userRole: UserRole) {
    userRole.roleName = this.selectedUserRole.roleName;
    userRole.active = this.selectedUserRole.active;
    userRole.isEnable = false;
    this.selectedUserRole = null;
  }

  onSave(userRole: UserRole) {
    this.selectedUserRole = null;
    (!userRole.userRoleId) ? this.userRoleService.saveUserRole(userRole, this.userRoles) : this.userRoleService.updateUserRole(userRole, this.userRoles);
  }

}
