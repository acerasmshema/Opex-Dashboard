import { Injectable } from "@angular/core";
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { PasswordDetail } from './password-detail.model';
import { Router } from '@angular/router';
import { ValidationService } from 'src/app/shared/service/validation/validation.service';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { MessageService } from 'primeng/primeng';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { PasswordChangeModel } from './password-change.model';

@Injectable()
export class PasswordService {

    private chnagePasswordURL = API_URL.user_api_URLs.CHANGE_PASSWORD;

    constructor(private formBuilder: FormBuilder,
        private messageService: MessageService,
        private apiCallService: ApiCallService,
        private statusService: StatusService,
        private validationService: ValidationService,
        private router: Router) { }

    createPasswordForm(): FormGroup {
        return this.formBuilder.group({
            currentPassword: new FormControl("", [Validators.required, Validators.minLength(8)]),
            newPassword: new FormControl("", [Validators.required, Validators.minLength(8)]),
            confirmPassword: new FormControl("", [Validators.required]),
        },
            { validator: this.validationService.mustMatchPassword('newPassword', 'confirmPassword') }
        );
    }

    changePassword(passwordForm: FormGroup) {
        if (passwordForm.invalid)
            return;

        let passwordChangeModel = new PasswordChangeModel();
        passwordChangeModel.userId = this.statusService.common.userDetail.userId;
        passwordChangeModel.newPassword = passwordForm.controls.confirmPassword.value;
        passwordChangeModel.currentPassword = passwordForm.controls.confirmPassword.value;

        this.apiCallService.callAPIwithData(this.chnagePasswordURL, passwordChangeModel).
            subscribe(
                response => {
                    this.messageService.add({ severity: "success", summary: '', detail: CommonMessage.SUCCESS.UPDATE_SUCCESS });
                    this.router.navigateByUrl('login');
                },
                error => {
                    passwordForm.controls.currentPassword.setErrors({ inCorrect: 'true' });
                }
            );
    }
}