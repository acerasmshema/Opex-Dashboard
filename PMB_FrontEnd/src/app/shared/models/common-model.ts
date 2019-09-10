import { MillDetail } from './mill-detail.model';
import { UserRole } from 'src/app/user-management/user-role/user-role.model';

export class CommonModel {
    public mills: MillDetail[] = [];
    public buTypes: any = [];
    public processLines: any = [];
    public selectedMill: MillDetail;
    public userRoles: UserRole[] = []

    constructor() {
        let userRole = new UserRole();
        userRole.roleName = "Admin";
        this.userRoles.push(userRole);

        userRole = new UserRole();
        userRole.roleName = "Department Head";
        this.userRoles.push(userRole);
    
        userRole = new UserRole();
        userRole.roleName = "MillOps";
        this.userRoles.push(userRole);
        
        userRole = new UserRole();
        userRole.roleName = "BCID";
        this.userRoles.push(userRole);
    }
}