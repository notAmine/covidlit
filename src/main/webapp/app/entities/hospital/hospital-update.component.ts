import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHospital, Hospital } from 'app/shared/model/hospital.model';
import { HospitalService } from './hospital.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-hospital-update',
  templateUrl: './hospital-update.component.html'
})
export class HospitalUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    streetAddress: [],
    city: [null, [Validators.required]],
    headOfSearvice: []
  });

  constructor(
    protected hospitalService: HospitalService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hospital }) => {
      this.updateForm(hospital);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(hospital: IHospital): void {
    this.editForm.patchValue({
      id: hospital.id,
      name: hospital.name,
      streetAddress: hospital.streetAddress,
      city: hospital.city,
      headOfSearvice: hospital.headOfSearvice
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hospital = this.createFromForm();
    if (hospital.id !== undefined) {
      this.subscribeToSaveResponse(this.hospitalService.update(hospital));
    } else {
      this.subscribeToSaveResponse(this.hospitalService.create(hospital));
    }
  }

  private createFromForm(): IHospital {
    return {
      ...new Hospital(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      streetAddress: this.editForm.get(['streetAddress'])!.value,
      city: this.editForm.get(['city'])!.value,
      headOfSearvice: this.editForm.get(['headOfSearvice'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHospital>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
