import { MillRole } from './mill-role.model';

export class UserDetail {
    userId: number;
    firstName: string;
    middleName: string;
    lastName: string;
    email: string;
    phone: string;
    createdBy: string;
    createdDate: string;
    address: string;
    isActive: boolean;
    country: string;
    username: string;
    role: string;
    updatedBy: string;
    millRoles: MillRole[] = [];
    isReadOnly: boolean;
    showDialog: boolean;
}