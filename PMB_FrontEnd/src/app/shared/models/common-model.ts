import { MillDetail } from './mill-detail.model';
import { Country } from './country.model';
import { UserDetail } from 'src/app/setup/user-management/user-detail/user-detail.model';
import { UserRole } from 'src/app/setup/user-management/user-role/user-role.model';
import { Department } from 'src/app/setup/user-management/user-detail/department.model';

export class CommonModel {
    public userDetail: UserDetail;
    public mills: MillDetail[] = [];
    public buTypes: any = [];
    public processLines: any = [];
    public selectedMill: MillDetail;
    public selectedRole: UserRole;
    public userRoles: UserRole[] = [];
    public activeUserRoles: UserRole[] = [];
    public departmentList: Department[] = [];
    public countryList: Country[] = [];
}