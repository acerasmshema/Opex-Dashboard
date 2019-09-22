import { Injectable } from "@angular/core";
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { StatusService } from 'src/app/shared/service/status.service';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { MessageService } from 'primeng/primeng';
import { ValidationService } from 'src/app/shared/service/validation/validation.service';

@Injectable()
export class ProfileEditService {

    updateUserURL = API_URL.user_api_URLs.UPDATE_USER;

    constructor(private formBuilder: FormBuilder,
        private messageService: MessageService,
        private commonService: CommonService,
        private apiCallService: ApiCallService,
        private validationService: ValidationService,
        private statusService: StatusService) { }

    createUserDetailForm(): FormGroup {
        const userDetail = this.statusService.common.userDetail;

        let userDetailForm = this.formBuilder.group({
            firstName: new FormControl(userDetail.firstName, [Validators.required]),
            lastName: new FormControl(userDetail.lastName, [Validators.required]),
            validateEmail: new FormControl(userDetail.email),
            email: new FormControl(userDetail.email, { validators: [Validators.required, Validators.email], asyncValidators: [this.validationService.forbiddenEmail.bind(this)], updateOn: 'blur' }),
            phone: new FormControl(userDetail.phone),
            address: new FormControl(userDetail.address),
            country: new FormControl(userDetail.country),
            countryList: this.formBuilder.array([]),
            department: new FormControl(userDetail.department),
            departmentList: this.formBuilder.array([])
        });

        userDetailForm.controls.email.valueChanges.
        subscribe((event) => {
            userDetailForm.get('email').setValue(event.toLowerCase(), { emitEvent: false });
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
        userDetail.country = userDetailForm.controls.country.value;
        userDetail.department = userDetailForm.controls.department.value;
        userDetail.updatedBy = userDetail.username;

        this.apiCallService.callPutAPIwithData(this.updateUserURL, userDetail).
            subscribe(
                (response: any) => {
                    this.messageService.add({ severity: "success", summary: '', detail: CommonMessage.SUCCESS.UPDATE_SUCCESS });
                },
                (error: any) => {
                    console.log("Error")
                }
            );
    }
}