import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.scss']
})
export class PasswordComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  onSave() {
    this.router.navigateByUrl('login');
  }
}
