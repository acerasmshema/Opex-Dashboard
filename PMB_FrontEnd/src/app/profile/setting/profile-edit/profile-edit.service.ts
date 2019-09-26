import { Injectable } from "@angular/core";
import { FormGroup } from '@angular/forms';
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { MessageService } from 'primeng/primeng';

@Injectable()
export class ProfileEditService {

    updateUserURL = API_URL.user_api_URLs.UPDATE_USER;
    profileURL = API_URL.apiURLs.USER_PROFILE;

    constructor(private messageService: MessageService,
        private apiCallService: ApiCallService,
        private statusService: StatusService) { }

    getProfile() {
        const userDetail = {
            userId: this.statusService.common.userDetail.userId
        }
        return this.apiCallService.callGetAPIwithData(this.profileURL, userDetail);
    }

    saveProfile(userDetailForm: FormGroup) {
        const userDetail = this.statusService.common.userDetail;
        userDetail.firstName = userDetailForm.controls.firstName.value;
        userDetail.lastName = userDetailForm.controls.lastName.value;
        userDetail.email = userDetailForm.controls.email.value;
        userDetail.phone = userDetailForm.controls.phone.value;
        userDetail.address = userDetailForm.controls.address.value;
        userDetail.country = userDetailForm.controls.country.value;
        userDetail.department = userDetailForm.controls.department.value;
        userDetail.updatedBy = userDetail.username;

        this.apiCallService.callPutAPIwithData(this.updateUserURL, userDetail).
            subscribe(
                (response: any) => {
                    this.messageService.add({ severity: "success", summary: '', detail: 'User details ' + CommonMessage.SUCCESS.UPDATE_SUCCESS });
                },
                (error: any) => {
                    console.log("Error")
                }
            );
    }
}