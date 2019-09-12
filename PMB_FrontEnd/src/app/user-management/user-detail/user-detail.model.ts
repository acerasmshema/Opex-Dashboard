import { MillRole } from './mill-role.model';
import { Department } from './department.model';
import { Country } from 'src/app/shared/models/country.model';

export class UserDetail {
    userId: number;
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    phone: string;
    createdBy: string;
    createdDate: string;
    address: string;
    isActive: boolean;
    country: string;
    countryList: Country[] = [];
    department: Department;
    departmentList: Department[];
    millRoles: MillRole[] = [];
    updatedBy: string;
    updatedDate: string;
    role: string;
    isReadOnly: boolean;
    showDialog: boolean;
}