import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { PasswordService } from './password.service';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.scss'],
  providers: [PasswordService]
})
export class PasswordComponent implements OnInit {

  passwordForm: FormGroup;

  constructor(private passwordDetailService: PasswordService) { }

  ngOnInit() {
    this.passwordForm = this.passwordDetailService.createPasswordForm();
  }

  onChangePassword() {
    if (this.passwordForm.invalid)
      return;
    this.passwordDetailService.changePassword(this.passwordForm);
  }
}
