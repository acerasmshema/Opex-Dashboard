import { Injectable } from "@angular/core";
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';

@Injectable()
export class ProfileEditService {

    constructor(private formBuilder: FormBuilder,
        private commonService: CommonService,
        private apiCallService: ApiCallService,
        private statusService: StatusService) { }

    createUserDetailForm(): FormGroup {
        const userDetail = this.statusService.common.userDetail;

        let userDetailForm = this.formBuilder.group({
            firstName: new FormControl(userDetail.firstName, [Validators.required]),
            lastName: new FormControl(userDetail.lastName, [Validators.required]),
            email: new FormControl(userDetail.email, [Validators.required, Validators.email]),
            phone: new FormControl(userDetail.phone),
            address: new FormControl(userDetail.address),
            country: new FormControl(userDetail.country),
            countryList: this.formBuilder.array([]),
            department: new FormControl(userDetail.department),
            departmentList: this.formBuilder.array([])
        });

        this.commonService.getAllCountry(userDetailForm);
        this.commonService.getAllDepartment(userDetailForm);

        return userDetailForm;
    }

    saveProfile(userDetailForm: FormGroup) {
        const userDetail = this.statusService.common.userDetail;
        userDetail.firstName = userDetailForm.controls.firstName.value;
        userDetail.lastName = userDetailForm.controls.lastName.value;
        userDetail.email = userDetailForm.controls.email.value;
        userDetail.phone = userDetailForm.controls.phone.value;
        userDetail.address = userDetailForm.controls.address.value;

        this.apiCallService.callAPIwithData("", userDetail).
            subscribe(() => {

            },
                (error: any) => {
                    console.log("Error")
                }
            );
    }
}