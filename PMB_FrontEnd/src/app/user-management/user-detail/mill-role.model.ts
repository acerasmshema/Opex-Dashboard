import { MillDetail } from '../../shared/models/mill-detail.model';
import { UserRole } from 'src/app/user-management/user-role/user-role.model';

export class MillRole {
    millRoleId: string;
    mills: MillDetail[] = [];
    userRoles: UserRole[] = []
    selectedMill: MillDetail;
    selectedUserRole: UserRole; 
}