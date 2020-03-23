import { IHospital } from 'app/shared/model/hospital.model';
import { BedStatus } from 'app/shared/model/enumerations/bed-status.model';

export interface IBed {
  id?: number;
  status?: BedStatus;
  hospital?: IHospital;
}

export class Bed implements IBed {
  constructor(public id?: number, public status?: BedStatus, public hospital?: IHospital) {}
}
