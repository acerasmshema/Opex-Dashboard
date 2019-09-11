import { Injectable } from "@angular/core";
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { UserDetail } from './user-detail.model';
import { MessageService } from 'primeng/primeng';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { MillRole } from './mill-role.model';

@Injectable()
export class UserDetailService {

    constructor(private statusService: StatusService,
        private messageService: MessageService,
        private formBuilder: FormBuilder,
        private apiCallService: ApiCallService) { }

    getUserDetailList(userList: UserDetail[]) {
        const millId = this.statusService.common.selectedMill.millId;

        for (let index = 1; index < 30; index++) {
            let userDetail = new UserDetail();
            userDetail.userId = index;

            if (index < 4) {
                userDetail.role = "Admin";
                userDetail.isActive = true;
            }
            else {
                userDetail.role = "Department Head";
                userDetail.isActive = false;
            }

            userDetail.createdDate = "03-09-2019"
            userDetail.username = "Sahil" + index;
            userDetail.firstName = "Sahil" + index;
            userDetail.address = "Baker Street " + index;
            userDetail.lastName = "Kalra" + index;
            userDetail.email = "sahil.kalra" + index + "@globallogic.com";
            userDetail.phone = "9696048000";
            userDetail.isReadOnly = false;
            userDetail.country = "India";

            let millRoles: MillRole[] = [];
            let millRole = new MillRole();
            millRole.mills = this.statusService.common.mills;
            millRole.userRoles = [];
            millRoles.push(millRole);
            userDetail.millRoles = millRoles;

            let millRoles1 = [];
            let millRole1 = new MillRole();
            millRole1.mills = this.statusService.common.mills;
            millRole1.userRoles = [];
            userDetail.millRoles.push(millRole);
            
             
            userList.push(userDetail);
        }

    }

    saveUserDetail(user: UserDetail, users: UserDetail[]) {
        const userDetail = users.find((user) => user.userId === user.userId)
        userDetail.isReadOnly = false;
        this.messageService.add({ severity: "success", summary: '', detail: "Updated Successfully" });
    }
}