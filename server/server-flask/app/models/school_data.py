from app.models import *


class MealModel(Document):
    date = StringField(primary_key=True)
    breakfast = ListField(required=True)
    lunch = ListField(required=True)
    dinner = ListField(required=True)


class ScheduleModel(Document):
    date = StringField(primary_key=True)
    schedules = ListField(required=True)
