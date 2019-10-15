import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserRole } from './user-role.model';
import { UserRoleService } from './user-role.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { Subscription } from 'rxjs';
import { MasterData } from 'src/app/shared/constant/MasterData';

@Component({
  selector: 'app-user-role',
  templateUrl: './user-role.component.html',
  styleUrls: ['./user-role.component.scss']
})
export class UserRoleComponent implements OnInit, OnDestroy {

  public userRoles: UserRole[] = [];
  roleSubscription: Subscription;

  cols = MasterData.userRoleCols;

  constructor(private userRoleService: UserRoleService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.userRoleService.getUserRoles(this.userRoles);

    this.roleSubscription = this.statusService.refreshUserList.
      subscribe((isRefresh: boolean) => {
        this.userRoles = [];
        this.userRoleService.getUserRoles(this.userRoles);
      })
  }

  onCreateUserRole() {
    let userRole = new UserRole();
    userRole.roleName = '';
    userRole.description = ''
    userRole.active = true;
    userRole.createdBy = this.statusService.common.userDetail.username;
    userRole.updatedBy = this.statusService.common.userDetail.username;
    userRole.operation = "Add";

    const data = {
      dialogName: "userRole",
      userRole: userRole
    }
    this.statusService.dialogSubject.next(data);
  }

  onEdit(userRoleId: string) {
    const userRole = this.userRoles.find((userRole) => userRole.userRoleId === userRoleId)
    userRole.operation = "Edit";

    const data = {
      dialogName: "userRole",
      userRole: userRole
    }
    this.statusService.dialogSubject.next(data);
  }

  ngOnDestroy() {
    this.roleSubscription.unsubscribe();
  }
}