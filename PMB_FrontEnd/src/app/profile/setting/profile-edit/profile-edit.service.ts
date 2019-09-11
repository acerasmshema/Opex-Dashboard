import { Injectable } from "@angular/core";
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';

@Injectable()
export class ProfileEditService {

    constructor(private formBuilder: FormBuilder,
        private statusService: StatusService) { }

    createUserDetailForm(): FormGroup {
        const userDetail = new UserDetail(); this.statusService.common.userDetail;

        //temp Code
        userDetail.firstName = "Sahil"

        let userDetailForm = this.formBuilder.group({
            'docDetails': this.formBuilder.array(this.statusService.common.departmentList),
            'country': this.formBuilder.array(this.statusService.common.countryList),
            'firstName': new FormControl(userDetail.firstName),
            'lastName': new FormControl(userDetail.lastName),
            'email': new FormControl(userDetail.email),
            'phone': new FormControl(userDetail.phone),
            'address': new FormControl(userDetail.address),
        });
        return userDetailForm;
    }
}