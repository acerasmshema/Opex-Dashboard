import { MillRole } from './mill-role.model';
import { Department } from './department.model';

export class UserDetail {
    userId: string;
    firstName: string;
    lastName: string;
    username: string;
    password: string;
    email: string;
    phone: string;
    address: string;
    active: boolean;
    country: string;
    department: Department;
    millRoles: MillRole[] = [];
    createdBy: string;
    updatedBy: string;
    role: string;
    showDialog: boolean;
    millRoleSortName: string;
}