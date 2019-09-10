import { Injectable } from "@angular/core";
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { PasswordDetail } from './password-detail.model';
import { Router } from '@angular/router';

@Injectable()
export class PasswordService {

    constructor(private formBuilder: FormBuilder,
        private router: Router) { }

    createPasswordForm(): FormGroup {
        return this.formBuilder.group({
            'currentPassword': new FormControl(""),
            'newPassword': new FormControl(""),
            'confirmPassword': new FormControl(""),
        });
    }

    //APi Call and error handling
    changePassword() {
        this.router.navigateByUrl('login');
    }
}