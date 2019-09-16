import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ProfileEditService } from './profile-edit.service';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss'],
  providers: [ProfileEditService]
})
export class ProfileEditComponent implements OnInit {

  userDetailForm: FormGroup;

  constructor(private profileEditService: ProfileEditService) { }

  ngOnInit() {
    this.userDetailForm = null;
    this.userDetailForm = this.profileEditService.createUserDetailForm();
  }

  onCancel() {
    this.userDetailForm = this.profileEditService.createUserDetailForm();
  }

  onEditProfileSave() {
    if (this.userDetailForm.valid)
      return;
    this.profileEditService.saveProfile(this.userDetailForm);
  }

}
