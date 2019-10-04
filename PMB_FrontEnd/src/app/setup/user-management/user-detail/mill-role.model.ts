import { MillDetail } from 'src/app/shared/models/mill-detail.model';
import { UserRole } from '../user-role/user-role.model';

export class MillRole {
    millRoleId: string;
    mills: MillDetail[] = [];
    userRoles: UserRole[] = []
    selectedMill: MillDetail;
    selectedUserRole: UserRole; 
}