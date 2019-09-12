import { MillDetail } from './mill-detail.model';
import { UserRole } from 'src/app/user-management/user-role/user-role.model';
import { Department } from 'src/app/user-management/user-detail/department.model';
import { Country } from './country.model';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';

export class CommonModel {
    public userDetail: UserDetail;
    public mills: MillDetail[] = [];
    public buTypes: any = [];
    public processLines: any = [];
    public selectedMill: MillDetail;
    public selectedRole: UserRole;
    public userRoles: UserRole[] = [];
    public departmentList: Department[] = [];
    public countryList: Country[] = [];
}